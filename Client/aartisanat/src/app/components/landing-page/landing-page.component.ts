import { Component, AfterViewInit } from '@angular/core';
import { TweenMax, Expo, Power3 } from 'gsap';
import { trigger, state, style, animate, transition } from '@angular/animations';
import { HttpClient } from '@angular/common/http';
import { Router } from '@angular/router';

// Now you can use TweenMax in your component or service



// ... (other animations)

@Component({
  selector: 'app-root',
  templateUrl: './landing-page.component.html',
  styleUrls: ['./landing-page.component.css'],
  animations: [
    trigger('ellipseRotate', [
      state('initial', style({ transform: 'rotate(-45deg)' })),
      state('final', style({ transform: 'rotate(-405deg)' })),
      transition('initial => final', animate('2s ease-in-out'))
    ])
  ]
})
export class LandingPageComponent implements AfterViewInit {
  ellipseRotationState = 'initial';
  constructor(

    private http: HttpClient,
    private router: Router
  ) {

  }
  login() {
    this.router.navigateByUrl('/login');
  }
  register() {
    this.router.navigateByUrl('/register');
  }
  ngAfterViewInit(): void {
    TweenMax.to(".overlay h1", 2, {
      opacity: 0,
      y: -60,
      ease: Expo.easeInOut
    })

    TweenMax.to(".overlay span", 2, {
      delay: .3,
      opacity: 0,
      y: -60,
      ease: Expo.easeInOut
    })

    TweenMax.to(".overlay", 2, {
      delay: 1,
      top: "-100%",
      ease: Expo.easeInOut
    })

    TweenMax.from(".ellipse-container", 1, {
      delay: 2,
      opacity: 6,
      ease: Expo.easeInOut
    })

    TweenMax.from(".yellow", 1, {
      delay: 3.5,
      opacity: 6,
      ease: Expo.easeInOut
    })

    TweenMax.from(".circle1", 1, {
      delay: 2.4,
      opacity: 6,
      ease: Expo.easeInOut
    })

    TweenMax.from(".circle2", 1, {
      delay: 2.6,
      opacity: 6,
      ease: Expo.easeInOut
    })

    TweenMax.from(".logo", 1, {
      delay: 3,
      opacity: 0,
      y: -100,
      ease: Expo.easeInOut
    })

    TweenMax.staggerFrom(".menu-links ul li", 1, {
      delay: 3.2,
      opacity: 0,
      x: -100,
      ease: Expo.easeInOut
    })

    TweenMax.from(".scrolldown", 1, {
      delay: 3.4,
      opacity: 0,
      y: 100,
      ease: Expo.easeInOut
    })

    TweenMax.from(".text .title", 1, {
      delay: 3,
      opacity: 0,
      x: 200,
      ease: Expo.easeInOut
    })

    TweenMax.from(".text p", 1, {
      delay: 3.2,
      opacity: 0,
      x: 200,
      ease: Expo.easeInOut
    })

    TweenMax.from(".watchnow", 1, {
      delay: 3.4,
      opacity: 0,
      x: 200,
      ease: Expo.easeInOut
    })

    TweenMax.staggerFrom(".media ul li", 1, {
      delay: 3,
      opacity: 0,
      y: 100,
      ease: Expo.easeInOut
    })
    console.log("Ellipse container:", document.querySelector(".ellipse-container"));

    TweenMax.from(".ellipse-container", 1, {
       delay: 2,
       opacity: 0,
       ease: Expo.easeInOut,
       onComplete: () => {
        // Trigger the ellipseRotate animation when TweenMax animation is complete
        this.ellipseRotationState = 'final';
    }
 })
}

}

