package ejercicio10;

/**
 * Interfaz común de los subsistemas regulatorios obligatorios.
 * Puede lanzar cualquier excepción: el sujeto la aísla y la registra.
 */
public interface RegulatoryObserver {

    String getName();

    void onRiskChanged(RiskChange change) throws Exception;
}
