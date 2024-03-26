package exercise.mapper;

import exercise.dto.ProductCreateDTO;
import exercise.dto.ProductDTO;
import exercise.dto.ProductUpdateDTO;
import exercise.model.Product;
import org.mapstruct.*;
import org.springframework.stereotype.Component;

// BEGIN
@Mapper(
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
@Component
public abstract class ProductMapper {
    @Mapping(target = "name", source = "title")
    @Mapping(target = "cost", source = "price")
    @Mapping(target = "barcode", source = "vendorCode")
    public abstract Product map(ProductCreateDTO productCreateDTO);

    @Mapping( target = "id", ignore = true )
    @Mapping( target = "title", source = "name" )
    @Mapping( target = "createdAt", ignore = true )
    @Mapping( target = "updatedAt", ignore = true )
    @Mapping( target = "price", source = "cost" )
    @Mapping( target = "vendorCode", source = "barcode" )
    public abstract ProductDTO map(Product model);

    @Mapping( target = "cost", source = "price" )
    public abstract void update(ProductUpdateDTO productUpdateDTO, @MappingTarget Product model);
}
// END
