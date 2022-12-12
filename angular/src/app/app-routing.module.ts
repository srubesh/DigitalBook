import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { AddBookComponent } from './add-book/add-book.component';
import { AllMyBooksComponent } from './all-my-books/all-my-books.component';
import { LoginComponent } from './login/login.component';
import { ProfileComponent } from './profile/profile.component';
import { ReadBookComponent } from './read-book/read-book.component';
import { RegisterComponent } from './register/register.component';
import { SearchBooksComponent } from './search-books/search-books.component';
import { SearchResultComponent } from './search-result/search-result.component';
import { SubscribedBooksComponent } from './subscribed-books/subscribed-books.component';

const routes: Routes = [
  { path: 'register', component: RegisterComponent },
  { path: 'login', component: LoginComponent },
  { path: 'profile', component: ProfileComponent},
  {path: 'allmybooks', component: AllMyBooksComponent},
  {path: 'subscribedbooks', component: SubscribedBooksComponent},
  {path: 'subscribedbooks/readbook', component: ReadBookComponent},
  {path: 'searchbook', component: SearchBooksComponent},
  {path: 'searchbook/searchresult', component: SearchResultComponent},
  {path: 'createbook', component: AddBookComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
