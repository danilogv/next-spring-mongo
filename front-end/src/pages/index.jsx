import {useRouter} from "next/router";

export default function Index() {
    if (typeof window !== 'undefined'){
        const rota = useRouter();
        rota.push("/usuario");
    }
}
