import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { LpaecommsSharedModule } from 'app/shared';
import {
    ClientsMySuffixComponent,
    ClientsMySuffixDetailComponent,
    ClientsMySuffixUpdateComponent,
    ClientsMySuffixDeletePopupComponent,
    ClientsMySuffixDeleteDialogComponent,
    clientsRoute,
    clientsPopupRoute
} from './';

const ENTITY_STATES = [...clientsRoute, ...clientsPopupRoute];

@NgModule({
    imports: [LpaecommsSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        ClientsMySuffixComponent,
        ClientsMySuffixDetailComponent,
        ClientsMySuffixUpdateComponent,
        ClientsMySuffixDeleteDialogComponent,
        ClientsMySuffixDeletePopupComponent
    ],
    entryComponents: [
        ClientsMySuffixComponent,
        ClientsMySuffixUpdateComponent,
        ClientsMySuffixDeleteDialogComponent,
        ClientsMySuffixDeletePopupComponent
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class LpaecommsClientsMySuffixModule {}
