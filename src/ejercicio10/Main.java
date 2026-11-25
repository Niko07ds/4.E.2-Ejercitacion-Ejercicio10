package ejercicio10;

import java.time.format.DateTimeFormatter;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class Main {

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");

    public static void main(String[] args) throws InterruptedException, ExecutionException {
        CorporateAccount account = new CorporateAccount("CTA-9001", RiskLevel.LOW);

        System.out.println("Registrando los subsistemas regulatorios obligatorios");
        account.addObserver(new AntiMoneyLaunderingSystem());
        account.addObserver(new LegalAnalysisSystem());
        account.addObserver(new InternationalRegulatorNotifier());

        System.out.println();
        System.out.println("Cambio 1: el riesgo de la cuenta pasa a Alto");
        System.out.println(formatReport(account.changeRiskLevel(RiskLevel.HIGH, "Operaciones con jurisdicciones de riesgo")));

        System.out.println();
        System.out.println("Cambio 2: el riesgo pasa a Severo (una entidad falla, las demás continúan)");
        System.out.println(formatReport(account.changeRiskLevel(RiskLevel.SEVERE, "Alerta por posible lavado de dinero")));

        System.out.println();
        System.out.println("Se registra un subsistema defectuoso que falla hasta al informar su nombre");
        account.addObserver(new BrokenAuditSystem());

        System.out.println();
        System.out.println("Cambio 3: el riesgo baja a Moderado");
        System.out.println(formatReport(account.changeRiskLevel(RiskLevel.MODERATE, "Documentación aclaratoria recibida")));

        System.out.println();
        System.out.println("Cambios 4 y 5: dos hilos modifican la misma cuenta al mismo tiempo");
        ExecutorService executor = Executors.newFixedThreadPool(2);
        try {
            Future<String> first = executor.submit(
                    () -> formatReport(account.changeRiskLevel(RiskLevel.HIGH, "Revisión del analista de riesgos")));
            Future<String> second = executor.submit(
                    () -> formatReport(account.changeRiskLevel(RiskLevel.LOW, "Auditoría externa satisfactoria")));
            System.out.println(first.get());
            System.out.println(second.get());
        } finally {
            executor.shutdown();
        }

        System.out.println();
        System.out.println("Historial de auditoría de la cuenta " + account.getAccountId());
        for (AuditReport report : account.getAuditTrail()) {
            System.out.println(report.change().previousLevel().getLabel() + " -> " + report.change().newLevel().getLabel()
                    + ": " + report.successCount() + " exitosa(s), " + report.failureCount() + " con incidencia");
        }
    }

    private static String formatReport(AuditReport report) {
        RiskChange change = report.change();
        StringBuilder text = new StringBuilder();
        text.append("Informe de auditoría (").append(change.timestamp().format(FORMATTER)).append(") | ")
                .append(change.previousLevel().getLabel()).append(" -> ").append(change.newLevel().getLabel());
        for (DeliveryRecord delivery : report.deliveries()) {
            text.append(System.lineSeparator()).append("  - ").append(delivery.entityName()).append(": ")
                    .append(delivery.status().getLabel()).append(" (").append(delivery.detail()).append(")");
        }
        return text.toString();
    }
}
