import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IInvoiceItemsMySuffix } from 'app/shared/model/invoice-items-my-suffix.model';
import { InvoiceItemsMySuffixService } from './invoice-items-my-suffix.service';

@Component({
    selector: 'jhi-invoice-items-my-suffix-delete-dialog',
    templateUrl: './invoice-items-my-suffix-delete-dialog.component.html'
})
export class InvoiceItemsMySuffixDeleteDialogComponent {
    invoiceItems: IInvoiceItemsMySuffix;

    constructor(
        private invoiceItemsService: InvoiceItemsMySuffixService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.invoiceItemsService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'invoiceItemsListModification',
                content: 'Deleted an invoiceItems'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-invoice-items-my-suffix-delete-popup',
    template: ''
})
export class InvoiceItemsMySuffixDeletePopupComponent implements OnInit, OnDestroy {
    private ngbModalRef: NgbModalRef;

    constructor(private activatedRoute: ActivatedRoute, private router: Router, private modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ invoiceItems }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(InvoiceItemsMySuffixDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.invoiceItems = invoiceItems;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate([{ outlets: { popup: null } }], { replaceUrl: true, queryParamsHandling: 'merge' });
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate([{ outlets: { popup: null } }], { replaceUrl: true, queryParamsHandling: 'merge' });
                        this.ngbModalRef = null;
                    }
                );
            }, 0);
        });
    }

    ngOnDestroy() {
        this.ngbModalRef = null;
    }
}
