import { Component, EventEmitter, OnInit, Output } from '@angular/core';
import { TokenStorageService } from '../_services/token-storage.service';
import { UserService } from '../_services/user.service';

@Component({
  selector: 'app-subscribed-books',
  templateUrl: './subscribed-books.component.html',
  styleUrls: ['./subscribed-books.component.css']
})
export class SubscribedBooksComponent  implements OnInit{

  constructor(private userService: UserService,private tokenStorageService: TokenStorageService) { }

  content?: string[];
  books :any = [];
  isLoggedIn = false;
  readerEmail : string = "";
  readerId : number = 0;
  message : any = null;

  ngOnInit(): void {
    this.isLoggedIn = !!this.tokenStorageService.getToken();
    if (this.isLoggedIn) {
      const user = this.tokenStorageService.getUser();
      this.readerEmail = user.email;
      this.readerId = user.id;
    }

    this.userService.getSubscribedBooks(this.readerEmail).subscribe(
      data => {
      this.books = data;
      //this.display(this.books);
      },
      err => {
        this.content = JSON.parse(err.error).message;
      }
    );

  }

  unSubscriveBook(bookId : number){
    //console.log(bookId+" "+block);

    this.userService.unSubscriveBook(this.readerEmail,this.readerId+"_"+bookId).subscribe(
      data => {
      this.message = data;
      alert(this.message.message);
      window.location.reload();
      },
      err => {
        this.content = JSON.parse(err.error).message;
      }
    );
    
  }

  read(bookId : string){
    sessionStorage.setItem("readBookId",bookId);
  }

}
