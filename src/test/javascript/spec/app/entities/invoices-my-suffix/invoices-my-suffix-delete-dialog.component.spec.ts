/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { LpaecommsTestModule } from '../../../test.module';
import { InvoicesMySuffixDeleteDialogComponent } from 'app/entities/invoices-my-suffix/invoices-my-suffix-delete-dialog.component';
import { InvoicesMySuffixService } from 'app/entities/invoices-my-suffix/invoices-my-suffix.service';

describe('Component Tests', () => {
    describe('InvoicesMySuffix Management Delete Component', () => {
        let comp: InvoicesMySuffixDeleteDialogComponent;
        let fixture: ComponentFixture<InvoicesMySuffixDeleteDialogComponent>;
        let service: InvoicesMySuffixService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [LpaecommsTestModule],
                declarations: [InvoicesMySuffixDeleteDialogComponent]
            })
                .overrideTemplate(InvoicesMySuffixDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(InvoicesMySuffixDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(InvoicesMySuffixService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('confirmDelete', () => {
            it('Should call delete service on confirmDelete', inject(
                [],
                fakeAsync(() => {
                    // GIVEN
                    spyOn(service, 'delete').and.returnValue(of({}));

                    // WHEN
                    comp.confirmDelete(123);
                    tick();

                    // THEN
                    expect(service.delete).toHaveBeenCalledWith(123);
                    expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
                })
            ));
        });
    });
});
