import { Component, OnInit } from '@angular/core';
import { TokenStorageService } from '../_services/token-storage.service';
import { UserService } from '../_services/user.service';

@Component({
  selector: 'app-add-book',
  templateUrl: './add-book.component.html',
  styleUrls: ['./add-book.component.css']
})
export class AddBookComponent implements OnInit {

  constructor(private userService: UserService, private tokenStorageService: TokenStorageService) { }

  isLoggedIn = false;
  authorEmail : string = "";
  authorId : number = 0;
  message : any = null;
  content?: string[];
  
  selectedFiles?: FileList;
  currentFile?: File;

  create = {
    title : "",
    category : "",
    price:"",
    publisher : "",
    active: "",
    content : ""
  }

  ngOnInit(): void {
    this.isLoggedIn = !!this.tokenStorageService.getToken();
    if (this.isLoggedIn) {
      const user = this.tokenStorageService.getUser();
      this.authorEmail = user.email;
      this.authorId = user.id;
    }
  }

  selectFile(event: any): void {
    this.selectedFiles = event.target.files;
  }

  


  createBook(){
    console.log(this.create.title);
    console.log(this.create.category);
    //console.log(this.create.image);
    console.log(this.create.price);
    console.log(this.create.publisher);
    console.log(this.create.active);
    console.log(this.create.content);

    if (this.selectedFiles) {
      const file: File | null = this.selectedFiles.item(0);
  
      if (file) {
        this.currentFile = file;
        this.userService.createBook(this.currentFile,this.create, this.authorId).subscribe(
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
    }
  }

}
