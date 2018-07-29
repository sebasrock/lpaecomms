package co.bassan.lpaecomms.service.mapper;

import co.bassan.lpaecomms.domain.*;
import co.bassan.lpaecomms.service.dto.ClientsDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Clients and its DTO ClientsDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface ClientsMapper extends EntityMapper<ClientsDTO, Clients> {


    @Mapping(target = "clientIDS", ignore = true)
    Clients toEntity(ClientsDTO clientsDTO);

    default Clients fromId(Long id) {
        if (id == null) {
            return null;
        }
        Clients clients = new Clients();
        clients.setId(id);
        return clients;
    }
}
