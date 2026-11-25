package ejercicio10;

/**
 * Niveles de riesgo de una cuenta corporativa.
 */
public enum RiskLevel {
    LOW("Bajo"),
    MODERATE("Moderado"),
    HIGH("Alto"),
    SEVERE("Severo");

    private final String label;

    RiskLevel(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}
