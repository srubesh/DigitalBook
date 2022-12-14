import { Component, OnInit } from '@angular/core';
import { TokenStorageService } from '../_services/token-storage.service';
import { UserService } from '../_services/user.service';

@Component({
  selector: 'app-update-book',
  templateUrl: './update-book.component.html',
  styleUrls: ['./update-book.component.css']
})
export class UpdateBookComponent implements OnInit {

  constructor(private userService: UserService, private tokenStorageService: TokenStorageService) { }

  isLoggedIn = false;
  authorEmail: string = "";
  authorId: number = 0;
  message: any = null;
  content?: string[];
  bookId: any = "";
  books: any = [];

  selectedFiles?: FileList;
  currentFile?: File;

  update = {
    title: "",
    category: "",
    price: "",
    publisher: "",
    active: "",
    content: ""
  }

  ngOnInit(): void {
    this.isLoggedIn = !!this.tokenStorageService.getToken();
    if (this.isLoggedIn) {
      const user = this.tokenStorageService.getUser();
      this.authorEmail = user.email;
      this.authorId = user.id;

      this.bookId = sessionStorage.getItem("updateBookId");
      this.userService.getBook(this.bookId).subscribe(
        data => {
          this.books = data;
          console.log(data);
          this.update.title = this.books.title;
          this.update.category = this.books.category;
          this.update.price = this.books.price;
          this.update.publisher = this.books.publisher;
          this.update.active = this.books.active;
          this.update.content = this.books.content;
        },
        err => {
          this.content = JSON.parse(err.error).message;
        }
      );
    }
  }

  selectFile(event: any): void {
    this.selectedFiles = event.target.files;
  }




  updateBook() {
    console.log(this.update.title);
    console.log(this.update.category);
    //console.log(this.create.image);
    console.log(this.update.price);
    console.log(this.update.publisher);
    console.log(this.update.active);
    console.log(this.update.content);

    if (this.selectedFiles) {
      const file: File | null = this.selectedFiles.item(0);

      if (file) {
        this.currentFile = file;
        this.userService.updateBook(this.currentFile, this.update, this.authorId, this.bookId).subscribe(
          data => {
            this.message = data;
            alert(this.message.message);
            
            window.location.reload();
          },
          err => {
            console.log(err)
            this.content = JSON.parse(err.error).message;
          }
        );
      }
    }
  }

}
