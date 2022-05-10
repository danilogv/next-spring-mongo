export default function Espera() {
    return (
        <div className="position-absolute top-50 start-50 w-auto">
            <div className="spinner-border text-secondary" role="status">
                <span className="visually-hidden"> Carregando... </span>
            </div>
        </div>
    );
}
