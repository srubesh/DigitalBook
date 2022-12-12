import { HttpClientModule } from '@angular/common/http';
import { NgModule } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { LoginComponent } from './login/login.component';
import { ProfileComponent } from './profile/profile.component';
import { RegisterComponent } from './register/register.component';
import { authInterceptorProviders } from './_helpers/auth.interceptor';
import { AllMyBooksComponent } from './all-my-books/all-my-books.component';
import { ImagePipePipe } from './image-pipe.pipe';
import { SubscribedBooksComponent } from './subscribed-books/subscribed-books.component';
import { ReadBookComponent } from './read-book/read-book.component';
import { SearchBooksComponent } from './search-books/search-books.component';
import { SearchResultComponent } from './search-result/search-result.component';
import { AddBookComponent } from './add-book/add-book.component';



@NgModule({
  declarations: [
    AppComponent,
    RegisterComponent,
    LoginComponent,
    ProfileComponent,
    AllMyBooksComponent,
    ImagePipePipe,
    SubscribedBooksComponent,
    ReadBookComponent,
    SearchBooksComponent,
    SearchResultComponent,
    AddBookComponent

  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    FormsModule,
    HttpClientModule
  ],
  providers: [authInterceptorProviders],
  bootstrap: [AppComponent]
})
export class AppModule { }
