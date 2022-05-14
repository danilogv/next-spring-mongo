import {useState,useEffect} from "react";
import {useRouter} from "next/router";
import Link from "next/link";
import Notiflix from "notiflix";
import moment from "moment";
import {mascaraCpf,cpfValido,formataDecimal,separadorMilhar} from "../global/funcoes.js";
import {URL_FUNCIONARIO} from "../global/variaveis.js";
import Espera from "./espera.jsx";

export default function FormularioFuncionario(props) {
    const [funcionario,alteraFuncionario] = useState(
        {
            nome: "",
            cpf: "",
            salario: "",
            idade: "",
            dataDesligamento: "",
            empresa: undefined
        }
    );
    let {nome,cpf,salario,idade,dataDesligamento,empresa} = funcionario || props.funcionario;
    const [esperar,alteraEsperar] = useState(false);
    const rota = useRouter();

    useEffect(() => {
        Notiflix.Notify.init({showOnlyTheLastOne: true});
        if (props.funcionario) {
            let func = props.funcionario;
            func.salario = func.salario.toLocaleString("pt-br",{minimumFractionDigits: 2,maximumFractionDigits: 2});
            alteraFuncionario(func);
        }
    }, [props]);

    function confirmarRemocao(id) {
        const msg = "Deseja realmente excluir esse funcionário?";
        Notiflix.Confirm.show("Confirmação",msg,"Sim","Não",() => submeterFormulario(id));
    }

    function preparaDadosBackend() {
        let funcionarioBack = {...funcionario};
        funcionarioBack.salario = funcionarioBack.salario.replace(".","").trim();
        funcionarioBack.salario = funcionarioBack.salario.replace(",",".").trim();
        funcionarioBack.salario = parseFloat(funcionarioBack.salario);
        funcionarioBack.idade = parseInt(funcionarioBack.idade);
        return funcionarioBack;
    }

    async function submeterFormulario() {
        if (!cpfValido(cpf)) {
            Notiflix.Notify.failure("CPF inválido.", {timeout: 5000});
            return;
        }
        try {
            alteraEsperar(true);
            if (nome !== "" && cpf !== "" && salario && idade && empresa) {
                const funcionarioBack = preparaDadosBackend();
                if (props.funcionario) {
                    const id = props.funcionario.id;
                    if (props.ehExclusao) {
                        await fetch(URL_FUNCIONARIO + "/" + id, {method: "DELETE",body: JSON.stringify(funcionarioBack)});
                    }
                    else {
                        await fetch(URL_FUNCIONARIO + "/" + id, {method: "PUT",body: JSON.stringify(funcionarioBack)});
                    }
                }
                else {
                    await fetch(URL_FUNCIONARIO, {method: "POST",body: JSON.stringify(funcionarioBack)});
                }
                Notiflix.Notify.success("Cadastro realizado com sucesso.", {timeout: 5000});
                rota.push("/funcionario/listar");
            }
        }
        catch (erro) {
            Notiflix.Notify.failure("Erro de servidor.", {timeout: 5000});
        }
        finally {
            alteraEsperar(false);
        }
    }

    return (
        <div className="col-sm-10">
            <div className="container-fluid mt-3">
                <div className="row">
                    {
                        esperar
                        ?
                            <Espera />
                        :
                            undefined
                    }
                    <form className="was-validated">
                        <div className="row">
                            <div className="col-12 col-sm-6">
                                <label htmlFor="nome" className="form-label fw-bold"> Nome* </label>
                                <input type="text" id="nome" name="nome" onChange={(event) => alteraFuncionario({...funcionario,nome: event.target.value})} value={nome} className="form-control" readOnly={props.ehExclusao} required />
                                <div className="invalid-feedback"> 
                                    Informe o NOME. 
                                </div>
                            </div>
                            <div className="col-12 col-sm-4">
                                <label htmlFor="cpf" className="form-label fw-bold"> CPF* </label>
                                <input type="text" id="cpf" name="cpf" placeholder="000.000.000-00" onChange={(event) => alteraFuncionario({...funcionario,cpf: event.target.value})} value={mascaraCpf(cpf)} className="form-control" readOnly={props.ehExclusao} required />
                                <div className="invalid-feedback">
                                    Informe o CPF.
                                </div>
                            </div>
                        </div>
                        <br />
                        <div className="row">
                            <div className="col-12 col-sm-4">
                                <label htmlFor="salario" className="form-label fw-bold"> Salário* </label>
                                <input type="text" id="salario" name="salario" onChange={(event) => alteraFuncionario({...funcionario,salario: formataDecimal(event.target.value)})} onBlur={() => alteraFuncionario({...funcionario,salario: separadorMilhar(salario)})} value={salario} className="form-control" readOnly={props.ehExclusao} required />
                                <div className="invalid-feedback">
                                    Informe o SALÁRIO. 
                                </div>
                            </div>
                            <div className="col-12 col-sm-2">
                                <label htmlFor="idade" className="form-label fw-bold"> Idade* </label>
                                <input type="text" id="idade" name="idade" onChange={(event) => alteraFuncionario({...funcionario,idade: event.target.value})} value={idade} min={18} className="form-control" readOnly={props.ehExclusao} required />
                                <div className="invalid-feedback"> 
                                    Informe a IDADE. 
                                </div>
                            </div>
                            <div className="col-12 col-sm-4">
                                <label htmlFor="data-desligamento" className="form-label fw-bold"> Data do desligamento </label>
                                <input type="date" id="data-desligamento" name="data-desligamento" max={moment().format("YYYY-MM-DD")} onChange={(event) => alteraFuncionario({...funcionario,dataDesligamento: event.target.value})} value={dataDesligamento} className="form-control" readOnly={props.ehExclusao} />
                            </div>
                        </div>
                        <br/>
                        <div className="row">
                            <div className="col-12 col-sm-6">
                                <label htmlFor="empresa" className="form-label fw-bold"> Empresa* </label>
                                <select id="empresa" name="empresa" className="form-control" onChange={(event) => alteraFuncionario({...funcionario,empresa: {id: event.target.value}})} value={empresa ? empresa.id : undefined} readOnly={props.ehExclusao} required>
                                    <option value=""> Selecione ... </option>
                                    <option value={1}> Empresa 1 </option>
                                    <option value={2}> Empresa 2 </option>
                                </select>
                                <div className="invalid-feedback"> 
                                    Informe a EMPRESA. 
                                </div>
                            </div>
                        </div>
                        <br />
                        <button type="button" className="btn btn-primary" onClick={props.ehExclusao ? () => confirmarRemocao(props.funcionario.id) : () => submeterFormulario()}> 
                            {
                                props.ehExclusao
                                ?
                                    <span> Excluir </span> 
                                :
                                    <span> Cadastrar </span> 
                            }
                            
                        </button>
                        <Link href="/funcionario/listar">
                            <a className="mx-2">
                                <button type="button" className="btn btn-primary"> 
                                    Cancelar 
                                </button>
                            </a>
                        </Link>
                    </form>
                </div>
            </div>
        </div>
    );
}