package pra.lue11.empleoexpres.model.enums;

/**
 * @author luE11 on 29/08/23
 */
public enum JobApplicationState {

    APPLIED("Aplicado"), VIEWED("Visto"), PROCESS("En proceso"), REJECTED("Rechazado"), FINISHED("Finalizado");

    final String state;

    JobApplicationState(String state) {
        this.state = state;
    }

    public String getState() {
        return state;
    }
}
