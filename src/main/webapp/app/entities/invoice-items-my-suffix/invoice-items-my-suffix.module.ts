import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { LpaecommsSharedModule } from 'app/shared';
import {
    InvoiceItemsMySuffixComponent,
    InvoiceItemsMySuffixDetailComponent,
    InvoiceItemsMySuffixUpdateComponent,
    InvoiceItemsMySuffixDeletePopupComponent,
    InvoiceItemsMySuffixDeleteDialogComponent,
    invoiceItemsRoute,
    invoiceItemsPopupRoute
} from './';

const ENTITY_STATES = [...invoiceItemsRoute, ...invoiceItemsPopupRoute];

@NgModule({
    imports: [LpaecommsSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        InvoiceItemsMySuffixComponent,
        InvoiceItemsMySuffixDetailComponent,
        InvoiceItemsMySuffixUpdateComponent,
        InvoiceItemsMySuffixDeleteDialogComponent,
        InvoiceItemsMySuffixDeletePopupComponent
    ],
    entryComponents: [
        InvoiceItemsMySuffixComponent,
        InvoiceItemsMySuffixUpdateComponent,
        InvoiceItemsMySuffixDeleteDialogComponent,
        InvoiceItemsMySuffixDeletePopupComponent
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class LpaecommsInvoiceItemsMySuffixModule {}
