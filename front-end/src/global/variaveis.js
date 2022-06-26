let headers = new Headers();
headers.append("Access-Control-Allow-Origin","*");
headers.append("Content-Type","application/json");

export let cabecalho = headers;
export let token = "";
export const URL_EMPRESA = "http://localhost:8080/empresa";
export const URL_FUNCIONARIO = "http://localhost:8080/funcionario";
export const URL_USUARIO = "http://localhost:8080/usuario";
export const QTD_PAGINAS_INTERMEDIARIAS = 5;
