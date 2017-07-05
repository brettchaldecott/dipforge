const gulp = require('gulp');
const browserSync = require('browser-sync');
const wiredep = require('wiredep').stream;
const gulpInject = require('gulp-inject');

const conf = require('../conf/gulp.conf');

gulp.task('inject', inject);

function inject() {
  const injectStyles = gulp.src(conf.path.src('**/*.css'), {read: false});
  const injectScripts = gulp.src([
    conf.path.tmp('**/!(index).js'),
    conf.path.tmp('**/index.js'),
    `!${conf.path.tmp('**/*.spec.js')}`
  ]);

  const injectOptions = {
    ignorePath: [conf.paths.src, conf.paths.tmp],
    addRootSlash: false
  };


  return gulp.src(conf.path.src('index.html'))
    .pipe(gulpInject(injectStyles, injectOptions))
    .pipe(gulpInject(injectScripts, injectOptions))
    .pipe(wiredep(Object.assign({}, conf.wiredep)))
    .pipe(gulp.dest(conf.paths.src))
    .pipe(gulp.dest(conf.paths.tmp))
    .pipe(browserSync.stream());
}
