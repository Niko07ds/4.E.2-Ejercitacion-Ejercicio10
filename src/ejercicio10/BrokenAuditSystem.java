package ejercicio10;

/**
 * Subsistema defectuoso: falla incluso al informar su nombre.
 * Sirve para comprobar que el aislamiento de errores es completo.
 */
public class BrokenAuditSystem implements RegulatoryObserver {

    @Override
    public String getName() {
        throw new IllegalStateException("no se pudo obtener el nombre del subsistema");
    }

    @Override
    public void onRiskChanged(RiskChange change) {
        throw new IllegalStateException("fallo interno del subsistema");
    }
}
