import { Component, Input, OnInit } from '@angular/core';
import { TokenStorageService } from '../_services/token-storage.service';
import { UserService } from '../_services/user.service';

@Component({
  selector: 'app-read-book',
  templateUrl: './read-book.component.html',
  styleUrls: ['./read-book.component.css']
})
export class ReadBookComponent implements OnInit{

  constructor(private userService: UserService,private tokenStorageService: TokenStorageService) { }

  content?: string[];
  books :any = [];
  isLoggedIn = false;
  readerEmail : string = "";
  readerId : number = 0;
  message : any = null;
  bookId : any = "";

  ngOnInit(): void {
    
    this.isLoggedIn = !!this.tokenStorageService.getToken();
    if (this.isLoggedIn) {
      const user = this.tokenStorageService.getUser();
      this.readerEmail = user.email;
      this.readerId = user.id;
    }

    this.bookId = sessionStorage.getItem("readBookId");
    this.userService.getBookContent(this.readerEmail,this.readerId+"_"+this.bookId).subscribe(
      data => {
      this.books = data;
      console.log(this.books);
      //this.display(this.books);
      },
      err => {
        this.content = JSON.parse(err.error).message;
      }
    );

  }
}
