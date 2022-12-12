import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AllMyBooksComponent } from './all-my-books.component';

describe('AllMyBooksComponent', () => {
  let component: AllMyBooksComponent;
  let fixture: ComponentFixture<AllMyBooksComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ AllMyBooksComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(AllMyBooksComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
