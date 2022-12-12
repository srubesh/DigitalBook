import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { AllMyBooksComponent } from './all-my-books/all-my-books.component';
import { LoginComponent } from './login/login.component';
import { ProfileComponent } from './profile/profile.component';
import { ReadBookComponent } from './read-book/read-book.component';
import { RegisterComponent } from './register/register.component';
import { SubscribedBooksComponent } from './subscribed-books/subscribed-books.component';

const routes: Routes = [
  { path: 'register', component: RegisterComponent },
  { path: 'login', component: LoginComponent },
  { path: 'profile', component: ProfileComponent},
  {path: 'allmybooks', component: AllMyBooksComponent},
  {path: 'subscribedbooks', component: SubscribedBooksComponent},
  {path: 'subscribedbooks/readbook', component: ReadBookComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
