import { IInvoicesMySuffix } from 'app/shared/model//invoices-my-suffix.model';

export interface IClientsMySuffix {
    id?: number;
    clientID?: string;
    clientFirstName?: string;
    clientLastName?: string;
    clientAddress?: string;
    clientPhone?: string;
    clientStatus?: boolean;
    clientIDS?: IInvoicesMySuffix[];
}

export class ClientsMySuffix implements IClientsMySuffix {
    constructor(
        public id?: number,
        public clientID?: string,
        public clientFirstName?: string,
        public clientLastName?: string,
        public clientAddress?: string,
        public clientPhone?: string,
        public clientStatus?: boolean,
        public clientIDS?: IInvoicesMySuffix[]
    ) {
        this.clientStatus = false;
    }
}
