package com.projet.enumeration;

/**
 * The enum Meeting type enum.
 */
public enum MeetingTypeEnum {

    /**
     * Task meeting type enum.
     */
    TASK("Tâche"),
    /**
     * Session meeting type enum.
     */
    SESSION("Séance"),
    /**
     * Appointment meeting type enum.
     */
    APPOINTMENT("Rendez-vous"),
    /**
     * None meeting type enum.
     */
    NONE("Aucun");

    private String label;

    MeetingTypeEnum(String label) {
        this.label = label;
    }

    /**
     * Gets label.
     *
     * @return the label
     */
    public String getLabel() {
        return label;
    }
}
