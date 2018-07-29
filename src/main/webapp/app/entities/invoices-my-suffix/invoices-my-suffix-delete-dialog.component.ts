import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IInvoicesMySuffix } from 'app/shared/model/invoices-my-suffix.model';
import { InvoicesMySuffixService } from './invoices-my-suffix.service';

@Component({
    selector: 'jhi-invoices-my-suffix-delete-dialog',
    templateUrl: './invoices-my-suffix-delete-dialog.component.html'
})
export class InvoicesMySuffixDeleteDialogComponent {
    invoices: IInvoicesMySuffix;

    constructor(
        private invoicesService: InvoicesMySuffixService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.invoicesService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'invoicesListModification',
                content: 'Deleted an invoices'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-invoices-my-suffix-delete-popup',
    template: ''
})
export class InvoicesMySuffixDeletePopupComponent implements OnInit, OnDestroy {
    private ngbModalRef: NgbModalRef;

    constructor(private activatedRoute: ActivatedRoute, private router: Router, private modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ invoices }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(InvoicesMySuffixDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.invoices = invoices;
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
