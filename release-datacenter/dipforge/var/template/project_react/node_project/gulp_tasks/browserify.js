var browserify = require('gulp-browserify');
var gulp = require('gulp');
var rename = require('gulp-rename');
var source = require('vinyl-source-stream');

const conf = require('../conf/gulp.conf');
const browserifyConfig = require('../src/deps.js');

gulp.task('browserify', function() {
    return gulp.src(conf.paths.src + "/deps.js")
        .pipe(browserify({
              transform: ['babelify'],
              insertGlobals : true,
              debug : true
            }))
        .on('prebundle', function(bundle) {
            for (let moduleName of browserifyConfig.requireConfig) {
                console.log("The module name is :" + moduleName)
              bundle.require(moduleName);
            }
            })
        .pipe(rename('bundle.js'))
        .pipe(gulp.dest(conf.paths.tmp + "/js"))
        .pipe(gulp.dest(conf.paths.src + "/js"));
});
