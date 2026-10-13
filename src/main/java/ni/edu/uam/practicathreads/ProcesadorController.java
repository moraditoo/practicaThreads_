package ni.edu.uam.practicathreads;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;

public class ProcesadorController {

    // Controles de Clientes
    @FXML private Label lblEstadoClientes;
    @FXML private Label lblPorcentajeClientes;
    @FXML private ProgressBar barraClientes;
    @FXML private Button btnIniciarClientes;
    @FXML private Button btnDetenerClientes;
    @FXML private Button btnReiniciarClientes;

    // Controles de Productos
    @FXML private Label lblEstadoProductos;
    @FXML private Label lblPorcentajeProductos;
    @FXML private ProgressBar barraProductos;
    @FXML private Button btnIniciarProductos;
    @FXML private Button btnDetenerProductos;
    @FXML private Button btnReiniciarProductos;

    private Thread hiloClientes;
    private Thread hiloProductos;

    private static final int TOTAL_CLIENTES = 100;
    private static final int TOTAL_PRODUCTOS = 50;

    // LÓGICA DE CLIENTES

    @FXML
    private void iniciarClientes() {
        if (hiloClientes != null && hiloClientes.isAlive()) {
            return;
        }

        barraClientes.setProgress(0);
        lblPorcentajeClientes.setText("0% (0/" + TOTAL_CLIENTES + ")");
        lblEstadoClientes.setText("Procesando clientes...");

        btnIniciarClientes.setDisable(true);
        btnDetenerClientes.setDisable(false);
        btnReiniciarClientes.setDisable(true);

        Runnable tareaClientes = () -> {
            try {
                for (int i = 1; i <= TOTAL_CLIENTES; i++) {
                    if (Thread.currentThread().isInterrupted()) {
                        throw new InterruptedException();
                    }

                    Thread.sleep(80); // 80ms por cliente (~8 segundos total)

                    final int actual = i;
                    final double progreso = (double) actual / TOTAL_CLIENTES;
                    final int porcentaje = (int) (progreso * 100);

                    Platform.runLater(() -> {
                        barraClientes.setProgress(progreso);
                        lblPorcentajeClientes.setText(porcentaje + "% (" + actual + "/" + TOTAL_CLIENTES + ")");
                    });
                }

                Platform.runLater(() -> {
                    lblEstadoClientes.setText("Clientes procesados correctamente");
                    habilitarBotonesClientes();
                });

            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                Platform.runLater(() -> {
                    lblEstadoClientes.setText("Proceso de clientes detenido");
                    habilitarBotonesClientes();
                });
            }
        };

        hiloClientes = new Thread(tareaClientes, "Hilo-Clientes");
        hiloClientes.setDaemon(true);
        hiloClientes.start();
    }

    @FXML
    private void detenerClientes() {
        if (hiloClientes != null && hiloClientes.isAlive()) {
            btnDetenerClientes.setDisable(true);
            lblEstadoClientes.setText("Solicitando detención...");
            hiloClientes.interrupt();
        }
    }

    @FXML
    private void reiniciarClientes() {
        if (hiloClientes != null && hiloClientes.isAlive()) {
            return;
        }
        barraClientes.setProgress(0);
        lblPorcentajeClientes.setText("0% (0/" + TOTAL_CLIENTES + ")");
        lblEstadoClientes.setText("Esperando inicio");
        habilitarBotonesClientes();
    }

    private void habilitarBotonesClientes() {
        btnIniciarClientes.setDisable(false);
        btnDetenerClientes.setDisable(true);
        btnReiniciarClientes.setDisable(false);
    }

    //LÓGICA DE PRODUCTOS

    @FXML
    private void iniciarProductos() {
        if (hiloProductos != null && hiloProductos.isAlive()) {
            return;
        }

        barraProductos.setProgress(0);
        lblPorcentajeProductos.setText("0% (0/" + TOTAL_PRODUCTOS + ")");
        lblEstadoProductos.setText("Procesando productos...");

        btnIniciarProductos.setDisable(true);
        btnDetenerProductos.setDisable(false);
        btnReiniciarProductos.setDisable(true);

        Runnable tareaProductos = () -> {
            try {
                for (int i = 1; i <= TOTAL_PRODUCTOS; i++) {
                    if (Thread.currentThread().isInterrupted()) {
                        throw new InterruptedException();
                    }

                    Thread.sleep(150); // 150ms por producto (~7.5 segundos total)

                    final int actual = i;
                    final double progreso = (double) actual / TOTAL_PRODUCTOS;
                    final int porcentaje = (int) (progreso * 100);

                    Platform.runLater(() -> {
                        barraProductos.setProgress(progreso);
                        lblPorcentajeProductos.setText(porcentaje + "% (" + actual + "/" + TOTAL_PRODUCTOS + ")");
                    });
                }

                Platform.runLater(() -> {
                    lblEstadoProductos.setText("Productos procesados correctamente");
                    habilitarBotonesProductos();
                });

            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                Platform.runLater(() -> {
                    lblEstadoProductos.setText("Proceso de productos detenido");
                    habilitarBotonesProductos();
                });
            }
        };

        hiloProductos = new Thread(tareaProductos, "Hilo-Productos");
        hiloProductos.setDaemon(true);
        hiloProductos.start();
    }

    @FXML
    private void detenerProductos() {
        if (hiloProductos != null && hiloProductos.isAlive()) {
            btnDetenerProductos.setDisable(true);
            lblEstadoProductos.setText("Solicitando detención...");
            hiloProductos.interrupt();
        }
    }

    @FXML
    private void reiniciarProductos() {
        if (hiloProductos != null && hiloProductos.isAlive()) {
            return;
        }
        barraProductos.setProgress(0);
        lblPorcentajeProductos.setText("0% (0/" + TOTAL_PRODUCTOS + ")");
        lblEstadoProductos.setText("Esperando inicio");
        habilitarBotonesProductos();
    }

    private void habilitarBotonesProductos() {
        btnIniciarProductos.setDisable(false);
        btnDetenerProductos.setDisable(true);
        btnReiniciarProductos.setDisable(false);
    }

    // IERRE DE VENTANA

    public void detenerAlCerrar() {
        if (hiloClientes != null && hiloClientes.isAlive()) {
            hiloClientes.interrupt();
        }
        if (hiloProductos != null && hiloProductos.isAlive()) {
            hiloProductos.interrupt();
        }
    }
}