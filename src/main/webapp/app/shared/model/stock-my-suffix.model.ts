import { IInvoiceItemsMySuffix } from 'app/shared/model//invoice-items-my-suffix.model';

export interface IStockMySuffix {
    id?: number;
    stockID?: string;
    stockName?: string;
    stockDesc?: string;
    stockOnHand?: string;
    stockPrice?: number;
    stockStatus?: boolean;
    stockIDS?: IInvoiceItemsMySuffix[];
}

export class StockMySuffix implements IStockMySuffix {
    constructor(
        public id?: number,
        public stockID?: string,
        public stockName?: string,
        public stockDesc?: string,
        public stockOnHand?: string,
        public stockPrice?: number,
        public stockStatus?: boolean,
        public stockIDS?: IInvoiceItemsMySuffix[]
    ) {
        this.stockStatus = false;
    }
}
