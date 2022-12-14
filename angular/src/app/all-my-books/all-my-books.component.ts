import { Component, OnInit } from '@angular/core';
import { TokenStorageService } from '../_services/token-storage.service';
import { UserService } from '../_services/user.service';

@Component({
  selector: 'app-all-my-books',
  templateUrl: './all-my-books.component.html',
  styleUrls: ['./all-my-books.component.css']
})
export class AllMyBooksComponent implements OnInit {
  content?: string[];

  books :any = [];
  blockMessage : any = null;
  //jsonVar  = {};
  // image : any= null;

  constructor(private userService: UserService,private tokenStorageService: TokenStorageService) { }
  
  isLoggedIn = false;
  authorId : number = 0;

  ngOnInit(): void {
    this.isLoggedIn = !!this.tokenStorageService.getToken();
    if (this.isLoggedIn) {
      const user = this.tokenStorageService.getUser();
      this.authorId = user.id;
    }
    this.userService.getAllBooks(this.authorId).subscribe(
      data => {
      this.books = data;
      //this.display(this.books);
      },
      err => {
        this.content = JSON.parse(err.error).message;
      }
    );

  }

  display(books : any) : void {
  }

  blockOrUnBlockBook(bookId : number, block : string){
    //console.log(bookId+" "+block);
    this.userService.blockOrUnblockBook(this.authorId,bookId,block).subscribe(
      data => {
      this.blockMessage = data;
      window.location.reload();
      },
      err => {
        this.content = JSON.parse(err.error).message;
      }
    );
    
  }

  editBook(bookId : string){
    sessionStorage.setItem("updateBookId",bookId);
  }
  
}
