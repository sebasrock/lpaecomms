export interface IInvoiceItemsMySuffix {
    id?: number;
    invitemNo?: string;
    invitemQty?: string;
    stockId?: number;
    invoicesId?: number;
}

export class InvoiceItemsMySuffix implements IInvoiceItemsMySuffix {
    constructor(
        public id?: number,
        public invitemNo?: string,
        public invitemQty?: string,
        public stockId?: number,
        public invoicesId?: number
    ) {}
}
