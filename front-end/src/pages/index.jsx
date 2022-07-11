import {useRouter} from "next/router";

export default function Index() {
    const rota = useRouter();

    if (typeof window !== "undefined")
        rota.push("/usuario");
}
