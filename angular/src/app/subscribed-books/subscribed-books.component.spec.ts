import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SubscribedBooksComponent } from './subscribed-books.component';

describe('SubscribedBooksComponent', () => {
  let component: SubscribedBooksComponent;
  let fixture: ComponentFixture<SubscribedBooksComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ SubscribedBooksComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(SubscribedBooksComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
