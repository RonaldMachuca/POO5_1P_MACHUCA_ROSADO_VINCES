package com.funcionamiento;

/**
 * Estados posibles de un producto en el pedido.
 */
public enum EstadoProducto {
    /** El producto está en preparación. */
    EN_PREPARACION,
    /** El producto está en ruta de entrega. */
    EN_RUTA,
    /** El producto fue entregado. */
    ENTREGADO
}
