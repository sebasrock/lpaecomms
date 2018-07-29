/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { LpaecommsTestModule } from '../../../test.module';
import { StockMySuffixDeleteDialogComponent } from 'app/entities/stock-my-suffix/stock-my-suffix-delete-dialog.component';
import { StockMySuffixService } from 'app/entities/stock-my-suffix/stock-my-suffix.service';

describe('Component Tests', () => {
    describe('StockMySuffix Management Delete Component', () => {
        let comp: StockMySuffixDeleteDialogComponent;
        let fixture: ComponentFixture<StockMySuffixDeleteDialogComponent>;
        let service: StockMySuffixService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [LpaecommsTestModule],
                declarations: [StockMySuffixDeleteDialogComponent]
            })
                .overrideTemplate(StockMySuffixDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(StockMySuffixDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(StockMySuffixService);
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
