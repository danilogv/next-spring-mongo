import {useRouter} from "next/router";

export default function Index() {
    const rota = useRouter();
    rota.push("/usuario");
}
