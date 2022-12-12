import { Component, OnInit } from '@angular/core';
import { TokenStorageService } from '../_services/token-storage.service';
import { UserService } from '../_services/user.service';

@Component({
  selector: 'app-search-result',
  templateUrl: './search-result.component.html',
  styleUrls: ['./search-result.component.css']
})
export class SearchResultComponent implements OnInit {

  constructor(private userService: UserService, private tokenStorageService: TokenStorageService) { }

  isLoggedIn = false;
  readerEmail : string = "";
  readerId : number = 0;
  content?: string[];
  books :any = [];
  searchQuery : any ="";
  subscribedBooks :any = [];
  subscribedBookId : number[] = [];
  message : any = null;

  ngOnInit(): void {
    this.isLoggedIn = !!this.tokenStorageService.getToken();
    if (this.isLoggedIn) {
      const user = this.tokenStorageService.getUser();
      this.readerEmail = user.email;
      this.readerId = user.id;

      this.userService.getSubscribedBooks(this.readerEmail).subscribe(
        data => {
        this.subscribedBooks = data;
        this.subscribedBooks.forEach((element : any) => {
          this.subscribedBookId.push(element.id);
        });
        },
        err => {
          this.content = JSON.parse(err.error).message;
        }
      );
    }

    this.searchQuery = sessionStorage.getItem("searchQuery");

      this.userService.searchBook(this.searchQuery).subscribe(
        data => {
        this.books = data;
        //console.log(this.books);
        //this.display(this.books);
        },
        err => {
          this.content = JSON.parse(err.error).message;
        }
      );
  }

  isSubscribed(bookId : number) : boolean{
    return this.subscribedBookId.includes(bookId);
  }

  subscriveBook(bookId : number){
    //console.log(bookId+" "+block);

    this.userService.subscribeBook(this.readerEmail,bookId).subscribe(
      data => {
      this.message = data;
      window.location.reload();
      },
      err => {
        this.content = err;
      }
    );
    
  }

}
