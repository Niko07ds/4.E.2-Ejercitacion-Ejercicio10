package ejercicio10;

/**
 * Subsistema de prevención de lavado de dinero.
 */
public class AntiMoneyLaunderingSystem implements RegulatoryObserver {

    @Override
    public String getName() {
        return "Prevención de lavado de dinero";
    }

    @Override
    public void onRiskChanged(RiskChange change) {
        System.out.println("Prevención de lavado de dinero: se analizan los movimientos de la cuenta " + change.accountId());
    }
}
