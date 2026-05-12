package mappers;

import dto.ProductoDto;
import models.Producto;

public class ProductoMapper {

    public Producto crear(ProductoDto dto) {
        Producto producto = new Producto();
        producto.setNombre(dto.getNombre());
        producto.setDescripcion(dto.getDescripcion());
        producto.setPrecio(dto.getPrecio());
        producto.setCategoria(dto.getCategoria());
        return producto.save(producto);
    }

    public Producto actualizar(Producto producto, ProductoDto dto) {
        producto.setNombre(dto.getNombre());
        producto.setDescripcion(dto.getDescripcion());
        producto.setPrecio(dto.getPrecio());
        producto.setCategoria(dto.getCategoria());
        return producto.save(producto);
    }

}
