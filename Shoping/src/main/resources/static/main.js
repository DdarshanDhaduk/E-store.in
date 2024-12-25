/*=============== SHOW MENU ===============*/

const  navMenu = document.getElementById('nav-menu'),
    navToggle = document.getElementById('nav-toggle'),
    navClose = document.getElementById('nav-close');

/*===== Menu Show =====*/
/* Validate if constant exists */

if (navToggle){
    navToggle.addEventListener('click', () => {
        navMenu.classList.add('show-menu');
    });
}

/*===== Hide Show =====*/
/* Validate if constant exists */
if (navClose){
    navClose.addEventListener('click', () => {
        navMenu.classList.remove('show-menu');
    });
}

/*=============== IMAGE GALLERY ===============*/

function imgGallery() {
    const mainImg = document.querySelector('.details__img'),
        smallImg = document.querySelectorAll('.details__small-img');

    smallImg.forEach((img) => {
        img.addEventListener('click', function () {
            mainImg.src = this.src;
        });
    });
}

imgGallery();

/*=============== SWIPER CATEGORIES ===============*/

var swiperCategories = new Swiper(".categories__container", {
        spaceBetween: 24,
        loop:true,

    navigation: {
    nextEl: ".swiper-button-next",
    prevEl: ".swiper-button-prev",
},

        breakpoints: {
            350: {
                slidesPerView: 2,
                spaceBetween: 24,
            },
            768: {
                slidesPerView: 3,
                spaceBetween: 24,
            },
            992: {
                slidesPerView: 4,
                spaceBetween: 24,
            },
            1200: {
                slidesPerView: 5,
                spaceBetween: 24,
            },
            1400: {
                slidesPerView: 6,
                spaceBetween: 24,
            },
        },
});

/*=============== SWIPER PRODUCTS ===============*/

var swiperProducts = new Swiper(".new__container ", {
    spaceBetween: 24,
    loop:true,

    navigation: {
        nextEl: ".swiper-button-next",
        prevEl: ".swiper-button-prev",
    },

    breakpoints: {
        768: {
            slidesPerView: 2,
            spaceBetween: 24,
        },
        992: {
            slidesPerView: 3,
            spaceBetween: 24,
        },
        1400: {
            slidesPerView: 4,
            spaceBetween: 24,
        },
    },
});

/*=============== PRODUCTS TABS ===============*/

const tabs=document.querySelectorAll('[data-target]'),
    tabContents=document.querySelectorAll('[content]');

tabs.forEach((tab)=>{
    tab.addEventListener('click',()=>{
        const target=document.querySelector(tab.dataset.target);
        tabContents.forEach((tabContent) => {
            tabContent.classList.remove('active-tab');
        });

        target.classList.add('active-tab');

        tabs.forEach((tab) => {
            tab.classList.remove('active-tab');
        });

        tab.classList.add('active-tab');
    });
});

/*=============== COLOR LINK ===============*/

//document.addEventListener('DOMContentLoaded', function () {
//    const colorLinks = document.querySelectorAll('.color__link');
//    const mainImageContainer = document.getElementById('main-image');
//    const thumbnailsContainer = document.getElementById('thumbnails');
//
//    colorLinks.forEach(link => {
//        link.addEventListener('click', function (e) {
//            e.preventDefault();
//
//            // Get the images array from data attribute
//            const images = JSON.parse(this.getAttribute('data-images'));
//
//            // Set the main image to the first image of the selected color
//            mainImageContainer.innerHTML = `<img src="${images[0]}" alt="Main Product Image" class="details__img">`;
//
//            // Clear existing thumbnails
//            thumbnailsContainer.innerHTML = '';
//
//            // Add image thumbnails
//            for (let i = 0; i < 3; i++) { // Only add the first three images
//                const img = document.createElement('img');
//                img.src = images[i];
//                img.alt = `Thumbnail ${i + 1}`;
//                img.classList.add('details__small-img');
//
//                // Change main image on thumbnail click
//                img.addEventListener('click', () => {
//                    mainImageContainer.innerHTML = `<img src="${img.src}" alt="Main Product Image" class="details__img">`;
//                    document.querySelectorAll('.details__small-img').forEach(i => i.classList.remove('active'));
//                    img.classList.add('active'); // Mark this thumbnail as active
//                });
//
//                thumbnailsContainer.appendChild(img);
//            }
//
//            // Add video thumbnail if it exists
//            if (images[3]) {
//                const videoThumbnail = this.querySelector('img[id^="video-thumbnail"]'); // Get the video thumbnail based on the id
//                const videoImg = document.createElement('img');
//                videoImg.src = videoThumbnail.src; // Use the src from the video thumbnail in HTML
//                videoImg.alt = "Video Thumbnail";
//                videoImg.classList.add('details__small-img');
//
//                // Change main image to video on click
//                videoImg.addEventListener('click', () => {
//                    const videoSrc = images[3]; // Get video source from the data
//                    mainImageContainer.innerHTML = `<video width="320" controls autoplay>
//                        <source src="${videoSrc}" type="video/mp4">
//                        Your browser does not support the video tag.
//                    </video>`;
//                });
//
//                thumbnailsContainer.appendChild(videoImg);
//            }
//
//            // Set the first thumbnail as active
//            if (thumbnailsContainer.querySelector('img')) {
//                thumbnailsContainer.querySelector('img').classList.add('active');
//            }
//        });
//    });
//
//    // Initialize the first color's images (if any)
//    if (colorLinks.length > 0) {
//        colorLinks[0].click();
//    }
//});


//document.addEventListener('DOMContentLoaded', function() {
//    const mainImageContainer = document.getElementById('main-image');
//    const thumbnailsContainer = document.getElementById('thumbnails');
//
//    document.querySelector('.color__list').addEventListener('change', handleColorChange);
//
//    // પ્રથમ રંગની છબીઓ સાથે પ્રારંભ કરો
//    const colorLinks = document.querySelectorAll('.color__link');
//    if (colorLinks.length > 0) {
//        colorLinks[0].click();
//    }
//});
//
//function handleColorChange(event) {
//    if (event.target.classList.contains('color__link')) {
//        const selectedColorId = event.target.value;
//
//        // જૂની છબીઓ અને થંબનેલ્સ સાફ કરો
//        mainImageContainer.innerHTML = '';
//        thumbnailsContainer.innerHTML = '';
//
//        // પસંદ કરેલા રંગ માટે છબીઓ અને વિડિઓઝ બતાવો
//        const allImages = document.querySelectorAll('.color-image, video');
//        let hasImages = false; // જો કોઈ છબીઓ દેખાય છે તે તપાસવા માટે
//
//        allImages.forEach(img => {
//            if (img.getAttribute('data-option') === selectedColorId) {
//                img.style.display = 'block'; // પસંદ કરેલા રંગની છબીઓ દર્શાવો
//
//                if (img.tagName === 'IMG') {
//                    addThumbnail(img);
//                    hasImages = true; // લક્ષ્ય રાખો કે કશા પણ છબીઓ બતાવવામાં આવી છે
//
//                    // પ્રથમ છબીને મુખ્ય છબી તરીકે સેટ કરો
//                    if (mainImageContainer.innerHTML === '') {
//                        mainImageContainer.innerHTML = `<img src="${img.src}" alt="Main Product Image" class="details__img">`;
//                    }
//                }
//            } else {
//                img.style.display = 'none'; // અન્ય છબીઓને છુપાવો
//            }
//        });
//
//        // જો કોઈ છબીઓ ન હોવ તો સૂચન કરો
//        if (!hasImages) {
//            mainImageContainer.innerHTML = '<p>આ રંગ માટે કોઈ છબીઓ ઉપલબ્ધ નથી.</p>'; // વકીલ: વપરાશકર્તાને જાણ કરવા માટે
//        }
//
//        // જો વિડિઓ ઉપલબ્ધ હોય તો તેનો વ્યવહાર કરો
//        handleVideo(selectedColorId);
//
//        // પ્રથમ થંબનેલને સક્રિય બનાવો
//        const thumbnails = thumbnailsContainer.querySelectorAll('img');
//        if (thumbnails.length > 0) {
//            thumbnails[0].classList.add('active');
//        }
//    }
//}
//
//function addThumbnail(img) {
//    const thumbnail = document.createElement('img');
//    thumbnail.src = img.src;
//    thumbnail.alt = "Thumbnail";
//    thumbnail.classList.add('details__small-img');
//
//    thumbnail.addEventListener('click', () => {
//        mainImageContainer.innerHTML = `<img src="${thumbnail.src}" alt="Main Product Image" class="details__img">`;
//    });
//
//    thumbnailsContainer.appendChild(thumbnail);
//}
//
//function handleVideo(selectedColorId) {
//    const video = document.querySelector(`video[data-option="${selectedColorId}"]`);
//    if (video) {
//        const videoThumbnail = document.createElement('img');
//        videoThumbnail.src = document.querySelector(`img[data-option="${selectedColorId}"][id="video-thumbnail-2"]`).src;
//        videoThumbnail.alt = "Video Thumbnail";
//        videoThumbnail.classList.add('details__small-img');
//
//        videoThumbnail.addEventListener('click', () => {
//            mainImageContainer.innerHTML = `<video width="320" controls autoplay>
//                <source src="${video.querySelector('source').src}" type="video/mp4">
//                Your browser does not support the video tag.
//            </video>`;
//        });
//
//        thumbnailsContainer.appendChild(videoThumbnail);
//    }
//}

/*=============== COUNTDOWN TIMER ===============*/

  const countdownElements = document.querySelectorAll('.countdown');
  countdownElements.forEach(countdown => {
      const endtime = new Date(countdown.getAttribute('data-endtime')).getTime();

      const interval = setInterval(() => {
          const now = new Date().getTime();
          const distance = endtime - now;

          if (distance < 0) {
              clearInterval(interval);
              countdown.innerHTML = "EXPIRED";
              return;
          }

          const days = Math.floor(distance / (1000 * 60 * 60 * 24));
          const hours = Math.floor((distance % (1000 * 60 * 60 * 24)) / (1000 * 60 * 60));
          const minutes = Math.floor((distance % (1000 * 60 * 60)) / (1000 * 60));
          const seconds = Math.floor((distance % (1000 * 60)) / 1000);

          countdown.querySelector('#days').textContent = days;
          countdown.querySelector('#hours').textContent = hours;
          countdown.querySelector('#minutes').textContent = minutes;
          countdown.querySelector('#seconds').textContent = seconds;
      }, 1000);
  });

/*=============== COUPON COPY CODE ===============*/

let copybtn = document.querySelector(".copybtn");


function copyIt(){
  let copyInput = document.querySelector('#copyvalue');

  copyInput.select();

  document.execCommand("copy");

  copybtn.textContent = "COPIED";
}

