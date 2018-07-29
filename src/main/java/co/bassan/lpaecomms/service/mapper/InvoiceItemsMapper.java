package co.bassan.lpaecomms.service.mapper;

import co.bassan.lpaecomms.domain.*;
import co.bassan.lpaecomms.service.dto.InvoiceItemsDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity InvoiceItems and its DTO InvoiceItemsDTO.
 */
@Mapper(componentModel = "spring", uses = {StockMapper.class, InvoicesMapper.class})
public interface InvoiceItemsMapper extends EntityMapper<InvoiceItemsDTO, InvoiceItems> {

    @Mapping(source = "stock.id", target = "stockId")
    @Mapping(source = "invoices.id", target = "invoicesId")
    InvoiceItemsDTO toDto(InvoiceItems invoiceItems);

    @Mapping(source = "stockId", target = "stock")
    @Mapping(source = "invoicesId", target = "invoices")
    InvoiceItems toEntity(InvoiceItemsDTO invoiceItemsDTO);

    default InvoiceItems fromId(Long id) {
        if (id == null) {
            return null;
        }
        InvoiceItems invoiceItems = new InvoiceItems();
        invoiceItems.setId(id);
        return invoiceItems;
    }
}
