import { Moment } from 'moment';
import { IInvoiceItemsMySuffix } from 'app/shared/model//invoice-items-my-suffix.model';

export interface IInvoicesMySuffix {
    id?: number;
    invNo?: string;
    invDate?: Moment;
    invAmount?: number;
    invStatus?: boolean;
    clientsId?: number;
    invNos?: IInvoiceItemsMySuffix[];
}

export class InvoicesMySuffix implements IInvoicesMySuffix {
    constructor(
        public id?: number,
        public invNo?: string,
        public invDate?: Moment,
        public invAmount?: number,
        public invStatus?: boolean,
        public clientsId?: number,
        public invNos?: IInvoiceItemsMySuffix[]
    ) {
        this.invStatus = false;
    }
}
