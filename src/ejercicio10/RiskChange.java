package ejercicio10;

import java.time.LocalDateTime;

/**
 * Cambio de nivel de riesgo. Es inmutable y viaja a todos los observadores regulatorios.
 */
public record RiskChange(String accountId, RiskLevel previousLevel, RiskLevel newLevel,
                         String reason, LocalDateTime timestamp) {
}
