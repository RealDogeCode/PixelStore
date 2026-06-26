document.addEventListener('DOMContentLoaded', () => {
  const carousel = document.querySelector('.carousel');
  const items = document.querySelectorAll('.carousel-item');

  if (!carousel || items.length === 0) return;

  let index = 0;
  let autoplayId = null;

  function goToSlide(i) {
    const max = items.length - 1;
    index = Math.max(0, Math.min(i, max));

    items[index].scrollIntoView({
      behavior: 'smooth',
      inline: 'start',
      block: 'nearest'
    });
  }

  function next() {
    if (index < items.length - 1) {
      goToSlide(index + 1);
    } else {
      goToSlide(0);
    }
  }

  function prev() {
    if (index > 0) {
      goToSlide(index - 1);
    } else {
      goToSlide(items.length - 1);
    }
  }

  function startAutoplay() {
    stopAutoplay();
    autoplayId = setInterval(next, 4000);
  }

  function stopAutoplay() {
    if (autoplayId) {
      clearInterval(autoplayId);
      autoplayId = null;
    }
  }

  carousel.addEventListener('scroll', () => {
    const width = carousel.offsetWidth;
    index = Math.round(carousel.scrollLeft / width);
  });

  carousel.addEventListener('mouseenter', stopAutoplay);
  carousel.addEventListener('mouseleave', startAutoplay);

  startAutoplay();

  window.carouselNext = next;
  window.carouselPrev = prev;
});