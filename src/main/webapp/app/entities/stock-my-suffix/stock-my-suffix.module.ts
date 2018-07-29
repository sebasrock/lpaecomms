import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { LpaecommsSharedModule } from 'app/shared';
import {
    StockMySuffixComponent,
    StockMySuffixDetailComponent,
    StockMySuffixUpdateComponent,
    StockMySuffixDeletePopupComponent,
    StockMySuffixDeleteDialogComponent,
    stockRoute,
    stockPopupRoute
} from './';

const ENTITY_STATES = [...stockRoute, ...stockPopupRoute];

@NgModule({
    imports: [LpaecommsSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        StockMySuffixComponent,
        StockMySuffixDetailComponent,
        StockMySuffixUpdateComponent,
        StockMySuffixDeleteDialogComponent,
        StockMySuffixDeletePopupComponent
    ],
    entryComponents: [
        StockMySuffixComponent,
        StockMySuffixUpdateComponent,
        StockMySuffixDeleteDialogComponent,
        StockMySuffixDeletePopupComponent
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class LpaecommsStockMySuffixModule {}
