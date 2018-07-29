import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { LpaecommsSharedModule } from 'app/shared';
import {
    InvoicesMySuffixComponent,
    InvoicesMySuffixDetailComponent,
    InvoicesMySuffixUpdateComponent,
    InvoicesMySuffixDeletePopupComponent,
    InvoicesMySuffixDeleteDialogComponent,
    invoicesRoute,
    invoicesPopupRoute
} from './';

const ENTITY_STATES = [...invoicesRoute, ...invoicesPopupRoute];

@NgModule({
    imports: [LpaecommsSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        InvoicesMySuffixComponent,
        InvoicesMySuffixDetailComponent,
        InvoicesMySuffixUpdateComponent,
        InvoicesMySuffixDeleteDialogComponent,
        InvoicesMySuffixDeletePopupComponent
    ],
    entryComponents: [
        InvoicesMySuffixComponent,
        InvoicesMySuffixUpdateComponent,
        InvoicesMySuffixDeleteDialogComponent,
        InvoicesMySuffixDeletePopupComponent
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class LpaecommsInvoicesMySuffixModule {}
