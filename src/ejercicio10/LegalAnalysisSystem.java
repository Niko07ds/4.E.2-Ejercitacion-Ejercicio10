package ejercicio10;

/**
 * Subsistema de análisis legal.
 */
public class LegalAnalysisSystem implements RegulatoryObserver {

    @Override
    public String getName() {
        return "Análisis legal";
    }

    @Override
    public void onRiskChanged(RiskChange change) {
        System.out.println("Análisis legal: se revisa la cuenta " + change.accountId() + " ("
                + change.previousLevel().getLabel() + " -> " + change.newLevel().getLabel() + ")");
    }
}
