import {TOKEN_EXPIROU,ERRO_SERVIDOR} from "./variaveis";

export function mascaraCnpj(cnpj) {
    return cnpj.replace(/\D+/g, "")
      .replace(/(\d{2})(\d)/, "$1.$2")
      .replace(/(\d{3})(\d)/, "$1.$2")
      .replace(/(\d{3})(\d)/, "$1/$2")
      .replace(/(\d{4})(\d)/, "$1-$2")
      .replace(/(-\d{2})\d+?$/, "$1")
    ;
}

export function mascaraCpf(cpf) {
    return cpf.replace(/\D+/g, "")
      .replace(/(\d{3})(\d)/, "$1.$2")
      .replace(/(\d{3})(\d)/, "$1.$2")
      .replace(/(\d{3})(\d)/, "$1-$2")
      .replace(/(-\d{2})\d+?$/, "$1")
    ;
}

export function cnpjValido(cnpj) {
    cnpj = cnpj.replace(/[^\d]+/g,"");
    if (cnpj.length !== 14) 
        return false;
    if (cnpj === "00000000000000")
        return false;
    if (cnpj === "11111111111111")
        return false;
    if (cnpj === "22222222222222")
        return false;
    if (cnpj === "33333333333333")
        return false;
    if (cnpj === "44444444444444")
        return false;
    if (cnpj === "55555555555555")
        return false;
    if (cnpj === "66666666666666")
        return false;
    if (cnpj === "77777777777777")
        return false;
    if (cnpj === "88888888888888")
        return false;
    if (cnpj === "99999999999999")
        return false;
    let tamanho = cnpj.length - 2;
    let numeros = cnpj.substring(0, tamanho);
    let digitos = cnpj.substring(tamanho);
    let soma = 0;
    let pos = tamanho - 7;
    for (let i = tamanho; i >= 1; i--) {
        soma += numeros.charAt(tamanho - i) * pos--;
        if (pos < 2)
            pos = 9;
    }
    let resultado = soma % 11 < 2 ? 0 : 11 - soma % 11;
    if (resultado !== parseInt(digitos.charAt(0)))
        return false;
    tamanho = tamanho + 1;
    numeros = cnpj.substring(0, tamanho);
    soma = 0;
    pos = tamanho - 7;
    for (let i = tamanho; i >= 1; i--) {
        soma += numeros.charAt(tamanho - i) * pos--;
        if (pos < 2)
            pos = 9;
    }
    resultado = soma % 11 < 2 ? 0 : 11 - soma % 11;
    if (resultado !== parseInt(digitos.charAt(1)))
        return false;
    return true;
}

export function cpfValido(cpf) {
    cpf = cpf.replace(/[^\d]+/g,'');
    if (cpf.length !== 11)
        return false;
    if (cpf === "00000000000")
        return false;
    if (cpf === "11111111111")
        return false;
    if (cpf === "22222222222")
        return false;
    if (cpf === "33333333333")
        return false;
    if (cpf === "44444444444")
        return false;
    if (cpf === "55555555555")
        return false;
    if (cpf === "66666666666")
        return false;
    if (cpf === "77777777777")
        return false;
    if (cpf === "88888888888")
        return false;
    if (cpf === "99999999999")
        return false;
    let soma = 0;
    for (let i = 0;i < 9;i++)
        soma += parseInt(cpf.charAt(i)) * (10 - i);
    let rev = 11 - (soma % 11);
    if (rev === 10 || rev === 11)
        rev = 0;
    if (rev !== parseInt(cpf.charAt(9)))
        return false;
    soma = 0;
    for (let i = 0;i < 10;i++)
        soma += parseInt(cpf.charAt(i)) * (11 - i);
    rev = 11 - (soma % 11);
    if (rev === 10 || rev === 11)
        rev = 0;
    if (rev !== parseInt(cpf.charAt(10)))
        return false;
    return true;
}

export function emailValido(email) {
    if (/^\w+([\.-]?\w+)*@\w+([\.-]?\w+)*(\.\w{2,3})+$/.test(email))
        return true;
    return false;
}

export function formataDecimal(valor) {
    if ((valor.match(/,/g) || []).length > 1) 
        valor = valor.substring(0,valor.length - 1);
    valor = valor.replace(/(,\d{2})\d*/g, "$1");
    valor = valor.replace(/[^\d,]+/g, "");
    return valor;
}

export function separadorMilhar(valor) {
    valor = valor.replace(/\B(?=(\d{3})+(?!\d))/g, ".");
    if (valor.indexOf(",") === -1)
        valor += ",00";
    else {
        const valorCentavos = valor.split(",");
        if (valorCentavos[1].length === 1)
            valor += "0";
    }
    return valor;
}

export async function obtemMensagemErro(resposta) {
    if (resposta && !resposta.ok) {
        if (resposta.status === 401)
            return TOKEN_EXPIROU;
        if (resposta.status) {
            const msg = await resposta.text();
            return msg;
        }
        return ERRO_SERVIDOR;
    }
    return "";
}
