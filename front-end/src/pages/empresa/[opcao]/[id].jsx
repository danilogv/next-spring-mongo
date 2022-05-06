import {useRouter} from "next/router";

export default function AcaoEmpresa() {
    const rota = useRouter();
    switch (rota.query.opcao) {
        case "visualizar":
            break;
        case "salvar":
            break;
        case "excluir":
            break;
    }
}
