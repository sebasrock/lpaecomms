package co.bassan.lpaecomms.service.mapper;

import co.bassan.lpaecomms.domain.*;
import co.bassan.lpaecomms.service.dto.InvoicesDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Invoices and its DTO InvoicesDTO.
 */
@Mapper(componentModel = "spring", uses = {ClientsMapper.class})
public interface InvoicesMapper extends EntityMapper<InvoicesDTO, Invoices> {

    @Mapping(source = "clients.id", target = "clientsId")
    InvoicesDTO toDto(Invoices invoices);

    @Mapping(source = "clientsId", target = "clients")
    @Mapping(target = "invNos", ignore = true)
    Invoices toEntity(InvoicesDTO invoicesDTO);

    default Invoices fromId(Long id) {
        if (id == null) {
            return null;
        }
        Invoices invoices = new Invoices();
        invoices.setId(id);
        return invoices;
    }
}
