import Link from "next/link";

export default function Rodape() {
    return (
        <div className="fixed-bottom">
            <footer className="text-center text-lg-start bg-light text-muted">
                <div className="text-center p-4" style={{backgroundColor: "lightgray"}}>
                    © 2022 Copyright:
                    <Link href="https://github.com/danilogv">
                        <a className="text-reset fw-bold" target="_blank" >
                            Danilo Gonçalves Vicente
                        </a>
                    </Link>
                </div>
            </footer>
        </div>
    );
}
