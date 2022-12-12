import { Pipe, PipeTransform } from '@angular/core';
import { DomSanitizer } from '@angular/platform-browser';

@Pipe({
  name: 'imagePipe'
})
export class ImagePipePipe implements PipeTransform {

  constructor(private sanitizer: DomSanitizer){}

  transform(value: any, ...args: unknown[]): any {
    let objectURL = 'data:image/png;base64,' + value;
    return this.sanitizer.bypassSecurityTrustUrl(objectURL);
    //return value.toUpperCase();
  }

}
