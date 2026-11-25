package ejercicio10;

/**
 * Notificación a organismos regulatorios internacionales.
 * Simula un tiempo de espera agotado cuando el riesgo pasa a severo.
 */
public class InternationalRegulatorNotifier implements RegulatoryObserver {

    @Override
    public String getName() {
        return "Organismos regulatorios internacionales";
    }

    @Override
    public void onRiskChanged(RiskChange change) {
        if (change.newLevel() == RiskLevel.SEVERE) {
            throw new IllegalStateException("la conexión con el organismo regulador agotó el tiempo de espera");
        }
        System.out.println("Organismos regulatorios: se envía la notificación obligatoria de la cuenta " + change.accountId());
    }
}
