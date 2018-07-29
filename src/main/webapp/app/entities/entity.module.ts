import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';

import { LpaecommsStockMySuffixModule } from './stock-my-suffix/stock-my-suffix.module';
import { LpaecommsClientsMySuffixModule } from './clients-my-suffix/clients-my-suffix.module';
import { LpaecommsInvoicesMySuffixModule } from './invoices-my-suffix/invoices-my-suffix.module';
import { LpaecommsInvoiceItemsMySuffixModule } from './invoice-items-my-suffix/invoice-items-my-suffix.module';
/* jhipster-needle-add-entity-module-import - JHipster will add entity modules imports here */

@NgModule({
    // prettier-ignore
    imports: [
        LpaecommsStockMySuffixModule,
        LpaecommsClientsMySuffixModule,
        LpaecommsInvoicesMySuffixModule,
        LpaecommsInvoiceItemsMySuffixModule,
        /* jhipster-needle-add-entity-module - JHipster will add entity modules here */
    ],
    declarations: [],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class LpaecommsEntityModule {}
